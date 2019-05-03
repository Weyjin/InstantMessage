package com.instant.message.service.impl;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.instant.message.service.QRCodeService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;

import static org.apache.catalina.manager.Constants.CHARSET;

@Service
public class QRCodeServiceImpl implements QRCodeService {
    @Override
    public String createQRCode(String content, int width, int height) throws IOException {

        if(!StringUtils.isEmpty(content)){

            ByteArrayOutputStream os=new ByteArrayOutputStream();
            Map<EncodeHintType,Comparable> hints=new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET,"utf-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
            hints.put(EncodeHintType.MARGIN,2);

            QRCodeWriter writer=new QRCodeWriter();
            try {
                BitMatrix bitMatrix=writer.encode(content, BarcodeFormat.QR_CODE,width,height,hints);
                BufferedImage bufferedImage= MatrixToImageWriter.toBufferedImage(bitMatrix);
                ImageIO.write(bufferedImage,"png",os);

               String resultImage=new String("data:image/png;base64,"+ Base64.encodeBase64String(os.toByteArray()));

                return resultImage;
            } catch (WriterException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                    if(os!=null){
                        os.flush();
                        os.close();
                    }
            }

        }
        return null;
    }

    @Override
    public String decode(String content) {

        InputStream input=null;
        try {
            Base64 decoder = new Base64();
                //Base64解码
                byte[] b = decoder.decode(content.replace("data:image/png;base64,",""));
                for(int i=0;i<b.length;++i)
                {
                    if(b[i]<0)
                    {//调整异常数据
                        b[i]+=256;
                    }
                }
             input=new ByteArrayInputStream(b);
            BufferedImage   image=ImageIO.read(input);
            if(image == null){
                return null;
            }

            BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Result result;
            Hashtable hints = new Hashtable();
            hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
            try {
                result = new MultiFormatReader().decode(bitmap, hints);
                String resultStr = result.getText();
                return resultStr;
            } catch (NotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(input!=null){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;

    }
}
