package com.example.mail;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

import androidx.appcompat.app.AppCompatActivity;

public class MainJava extends AppCompatActivity {
    EditText _Email, _TextMessage;
    Button _btnMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_test);
        _Email = findViewById(R.id.Email);
        _TextMessage = findViewById(R.id.TextMessage);
        _btnMessage = findViewById(R.id.btnMessage);
        _btnMessage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final String username = "pashkevich1617@gmail.com";
                final String password = "ykfoqgbumedzvxyb";
                String messageToSend = _TextMessage.getText().toString();
                Properties props=new Properties();
                props.put("mail.smtp.auth","true");
                props.put("mail.smtp.starttls.enable","true");
                props.put("mail.smtp.host","smtp.gmail.com");
                props.put("mail.smtp.port","587");
                Session session=Session.getInstance(props,
                        new javax.mail.Authenticator(){
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication(){
                                return new PasswordAuthentication(username, password);
                            }
                        });
                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(username));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(_Email.getText().toString()));
                    message.setSubject("Send");
                    message.setText(messageToSend);
                    Transport.send(message);
                    Toast.makeText(getApplicationContext(),"email luck",Toast.LENGTH_LONG).show();
                }catch (MessagingException e){
                    throw new RuntimeException(e);
                }
            }
        });
        StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }

}
