package ru.panic.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.panic.entity.RefLink;
import ru.panic.exception.RefLinkFoundedException;
import ru.panic.exception.RefLinkNotFoundException;
import ru.panic.mappers.RefLinkRepository;
import ru.panic.util.UrlGeneratorUtil;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class RefController {
    public RefController(RefLinkRepository refLinkRepository, UrlGeneratorUtil urlGeneratorUtil) {
        this.refLinkRepository = refLinkRepository;
        this.urlGeneratorUtil = urlGeneratorUtil;
    }

    private final RefLinkRepository refLinkRepository;
    private final UrlGeneratorUtil urlGeneratorUtil;

    @GetMapping("/factory/getAllRefLinks")
    public List<RefLink> getAllRefLinks() {
        log.info("Get all referral links request received");
        List<RefLink> allRefLinks = refLinkRepository.findAllRefLinksAndClientsAndProducts();
        log.debug("Found {} referral links", allRefLinks.size());
        return allRefLinks;
    }

    @GetMapping("/factory/getRefLinkById")
    public RefLink getRefLinkById(@RequestParam(name = "id") Long id) {
        log.info("Get referral link by ID request received, ID: {}", id);
        RefLink refLink = refLinkRepository.findById(id);
        if (refLink != null) {
            log.debug("Found referral link: {}", refLink);
        } else {
            log.debug("Referral link not found for ID: {}", id);
        }
        return refLink;
    }

    @PostMapping("/factory/createRefLink")
    public HashMap<String, Object> createRefLink(@RequestParam(name = "title") String title) {
        log.info("Create referral link request received, title: {}", title);
        if (refLinkRepository.findByTitle(title) != null) {
            log.warn("Referral link with the same title already exists: {}", title);
            throw new RefLinkFoundedException("Реферальная ссылка с таким названием уже существует");
        }
        String URL = urlGeneratorUtil.generate();
        refLinkRepository.save(title, URL, System.currentTimeMillis(), 0L, 0L, 0L, 0.0, 0.0);
        refLinkRepository.saveClientForRefLink(refLinkRepository.findByMaxIdByDesc(), null);
        log.debug("Referral link created: {}", URL);
        HashMap<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("title", title);
        response.put("url", URL);
        response.put("timestamp", System.currentTimeMillis());
        response.put("transitions", 0);
        response.put("registrations", 0);
        response.put("purchases", 0);
        response.put("pfu", 0);
        response.put("pfk", 0);
        return response;
    }

    @PutMapping("/factory/updateRefLink")
    public HashMap<String, Object> updateRefLink(
            @RequestParam(name = "url") String URL,
            @RequestParam(name = "ipAddress") String ipAddress
    ) {
        log.info("Update referral link request received, URL: {}, IP address: {}", URL, ipAddress);
        RefLink refLink = refLinkRepository.findByURL(URL);
        if (refLink != null) {
            refLink.setTransitions(refLink.getTransitions() + 1);
            refLinkRepository.update(refLink);
            refLinkRepository.saveClientForRefLink(refLink.getId(), ipAddress);
            log.debug("Referral link updated: {}", refLink);
            HashMap<String, Object> response = new HashMap<>();
            response.put("status", 200);
            return response;
        } else {
            log.warn("Referral link not found for URL: {}", URL);
            throw new RefLinkNotFoundException("Такой короткой ссылки не существует");
        }
    }

    @DeleteMapping("/factory/deleteRefLinkById")
    public HashMap<String, Integer> deleteRefLinkById(@RequestParam(name = "id") Long id) {
        log.info("Delete referral link request received, ID: {}", id);
        refLinkRepository.deleteById(id);
        log.debug("Referral link deleted with ID: {}", id);
        HashMap<String, Integer> response = new HashMap<>();
        response.put("status", 200);
        return response;
    }
}

