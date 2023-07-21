package com.sprinklr.botintern.Red.Requests.ApiController;

import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.builder.TurnContext;
import com.microsoft.bot.schema.Activity;
import com.microsoft.bot.schema.Attachment;
import com.microsoft.bot.schema.AttachmentData;
import com.microsoft.bot.schema.teams.FileInfoCard;
import com.sprinklr.botintern.Red.Requests.FinalApis.UMByUMId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.microsoft.bot.schema.TextFormatTypes;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Component
public class UniversalMessage {

    @Autowired
    UMByUMId umByUMId;

    public Activity getResult(TurnContext turnContext, String request){

        String replyString="";
        int idx=request.indexOf(" ");
        String partnerId="";
        String messageId="";
        if(idx==-1){
            replyString="Specify both the parameters";
        }else {
            String param1=request.substring(0,idx);
            try {
                int strToInt=Integer.parseInt(param1);
                partnerId=param1;
                messageId=request.substring(idx+1,request.length());
            }catch (Exception ex){
                messageId=param1;
                partnerId=request.substring(idx+1,request.length());
            }
            replyString= umByUMId.universalMessageById(messageId,partnerId);
        }

        try {
//            String fileName="UniversalMessage.txt";
//            File file=new File(fileName);
//            FileWriter writer=new FileWriter(file);
//            writer.write(replyString);
//            writer.close();
//
//            System.out.println(file.getPath());
//            System.out.println(file.toString());
//
//            Attachment fileAttachment=new Attachment();
//            String filePath= file.getPath();
//            fileAttachment.setName("UniversalMessage.txt");
//            fileAttachment.setContentType("application/vnd.microsoft.teams.file");
//            fileAttachment.setContentUrl(filePath);
//
//            Activity reply=MessageFactory.text("Universal Message");
//            reply.setAttachment(fileAttachment);
////            return turnContext.sendActivity(reply).thenApply(resourceResponse -> null);
//            return reply;
            String apiResponse = replyString;
            byte[] fileContent = apiResponse.getBytes(StandardCharsets.UTF_8);

            // Send the file as an attachment
            try {
                Attachment attachment = createFileAttachment(fileContent, "api_response.txt");
                Activity reply = MessageFactory.text("Here is the file from the API call:");
                reply.setAttachments(Collections.singletonList(attachment));
                return reply;
            } catch (Exception e) {
                e.printStackTrace();
                return MessageFactory.text("text file ni aa paari");
            }


        }catch (Exception ex){
            System.out.println("file ni bani");
            System.out.println(ex.getStackTrace());
            System.out.println(ex.getMessage());
        }


        Activity reply=MessageFactory.text(replyString);
        return  reply;
    }

    private static Attachment createFileAttachment(byte[] fileContent, String fileName) {
        AttachmentData attachmentData = new AttachmentData();
        attachmentData.setType(String.valueOf(TextFormatTypes.PLAIN));
        attachmentData.setName(fileName);
        attachmentData.setOriginalBase64(fileContent);

        Attachment attachment = new Attachment();
        attachment.setContent(attachmentData);
        return attachment;
    }


}
