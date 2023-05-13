package ru.panic.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.panic.dto.WooCommerceHookDto;
import ru.panic.entity.RefLink;
import ru.panic.mappers.RefLinkRepository;

@RestController
@RequestMapping("/api/hook")
public class WooCommerceApi {
    public WooCommerceApi(RefLinkRepository refLinkRepository) {
        this.refLinkRepository = refLinkRepository;
    }

    RefLinkRepository refLinkRepository;
    @PostMapping
    public void getPush(
            @RequestBody WooCommerceHookDto dto
            ){
        RefLink refLink = refLinkRepository.findById(refLinkRepository.getRefLinkIdByIpAddress(dto.getCustomer_ip_address()));
        refLink.setPurchases(refLink.getPurchases()+1);
        refLink.setPfk(refLink.getPfk()+dto.getTotal());
        refLink.setPfu(refLink.getPfu()/ refLink.getPurchases());
        refLinkRepository.update(refLink);
    }

}
