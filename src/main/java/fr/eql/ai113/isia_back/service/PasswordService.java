package fr.eql.ai113.isia_back.service;

import fr.eql.ai113.isia_back.service.impl.exception.PasswordGenerationException;

public interface PasswordService {
    String generateRandomPassword() throws PasswordGenerationException;
}
