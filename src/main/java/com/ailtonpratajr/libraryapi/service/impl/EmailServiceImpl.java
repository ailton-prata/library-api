package com.ailtonpratajr.libraryapi.service.impl;

import com.ailtonpratajr.libraryapi.service.EmailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    @Override
    public void sendMails(String mensagem, List<String> mailsList) {

    }
}
