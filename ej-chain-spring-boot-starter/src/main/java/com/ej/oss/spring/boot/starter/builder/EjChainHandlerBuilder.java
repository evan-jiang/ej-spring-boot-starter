package com.ej.oss.spring.boot.starter.builder;

import com.ej.chain.annotation.FromContext;
import com.ej.chain.annotation.ToContext;
import com.ej.chain.handlers.Handler;
import com.ej.chain.proxy.ProxyHandlerFactory;
import com.ej.oss.spring.boot.starter.annotation.EnableEjChain;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class EjChainHandlerBuilder implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        Map<String, Object> attributes = annotationMetadata.getAnnotationAttributes(EnableEjChain.class.getName());
        String[] packages = (String[]) attributes.get("packages");
        if (packages == null || packages.length == 0) {
            return;
        }
        Map<String, Class<?>> classes = getNormalOrProxyClass(packages);
        if (classes == null || classes.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Class<?>> e : classes.entrySet()) {
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(e.getValue());
            BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
            beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
            beanDefinitionRegistry.registerBeanDefinition(e.getKey(), beanDefinition);
        }
    }

    private Map<String, Class<?>> getNormalOrProxyClass(String[] packages) {
        EjClassPathScanningCandidateComponentProvider scanner = new EjClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AssignableTypeFilter(Handler.class));
        final Map<String, Class<?>> classes = new HashMap<>();
        for (String pck : packages) {
            scanner.findCandidateComponents(pck).stream().map(beanDefinition -> {
                try {
                    return Class.forName(beanDefinition.getBeanClassName());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return null;
            }).filter(clazz -> {
                return clazz != null;
            }).forEach(clazz -> {
                String beanName = firstLowerCase(clazz.getSimpleName());
                Class<?> newClass = clazz;
                if (Modifier.isAbstract(clazz.getModifiers())) {
                    newClass = ProxyHandlerFactory.getJavassistProxyHandlerClass(clazz);
                }
                classes.put(beanName, newClass);
            });
        }
        return classes;
    }

    private String firstLowerCase(String classSimpleName) {
        return String.valueOf(classSimpleName.charAt(0)).toLowerCase() + classSimpleName.substring(1);
    }

    class EjClassPathScanningCandidateComponentProvider extends ClassPathScanningCandidateComponentProvider {
        EjClassPathScanningCandidateComponentProvider(boolean useDefaultFilters) {
            super(useDefaultFilters);
        }

        protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
            AnnotationMetadata metadata = beanDefinition.getMetadata();
            return metadata.isIndependent() && (metadata.isConcrete() || metadata.isAbstract() && (metadata.hasAnnotatedMethods(FromContext.class.getName()) || metadata.hasAnnotatedMethods(ToContext.class.getName())));
        }
    }
}
