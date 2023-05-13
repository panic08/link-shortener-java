package ru.panic.util;

import org.springframework.stereotype.Component;
import ru.panic.mappers.RefLinkRepository;

import java.util.Random;

@Component
public class UrlGeneratorUtil {
    public UrlGeneratorUtil(RefLinkRepository refLinkRepository) {
        this.refLinkRepository = refLinkRepository;
    }

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int LENGTH = 5;
    private static final Random RANDOM = new Random();
    private final RefLinkRepository refLinkRepository;

    public String generate() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        if (refLinkRepository.findByURL(sb.toString()) != null){
            return generate();
        }
        return sb.toString();
    }
}
