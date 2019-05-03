package com.instant.message.service;

import java.io.IOException;

public interface QRCodeService {
    String createQRCode(String content,int width,int height) throws IOException;

    String decode(String content);
}
