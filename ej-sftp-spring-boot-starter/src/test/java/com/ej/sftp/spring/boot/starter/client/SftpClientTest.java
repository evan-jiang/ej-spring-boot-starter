package com.ej.sftp.spring.boot.starter.client;

import com.ej.sftp.spring.boot.starter.properties.SftpConfig;

public class SftpClientTest {

    public static void main(String[] args) {
        SftpConfig config = new SftpConfig();
        config.setHost("100.112.33.89");
        config.setPort(22);
        config.setUserName("wjs_ceb_creditcard_test");
        config.setPassWord("cVz3oYCn");
        SftpClient sftpClient = new SftpClient(config);
        sftpClient.downloadFile(
                "/ceb/creditcard/outfile/20190419/20190418_ZA_WDLoanDetail2.txt",
                "E:/x/x/20190418_ZA_WDLoanDetail1.txt");
        sftpClient.disconnect();
    }
}
