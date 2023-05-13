package ru.panic.mappers;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import ru.panic.dto.Client;
import ru.panic.entity.RefLink;

import java.util.List;

@Repository
@Mapper
public interface RefLinkRepository {
    @Select("SELECT r.*, c.ip_address, p.product, p.cost, p.purchased, p.receipt " +
            "FROM RefLinks r " +
            "LEFT JOIN RefLink_Client c ON r.id = c.reflink_id " +
            "LEFT JOIN RefLink_Product p ON r.id = p.reflink_id " +
            "WHERE r.title = #{title}")
    RefLink findByTitle(@Param("title") String title);

    @Select("SELECT r.*, c.ip_address, p.product, p.cost, p.purchased, p.receipt " +
            "FROM RefLinks r " +
            "LEFT JOIN RefLink_Client c ON r.id = c.reflink_id " +
            "LEFT JOIN RefLink_Product p ON r.id = p.reflink_id " +
            "WHERE r.URL = #{URL}")
    RefLink findByURL(@Param("URL") String URL);


    @Select("SELECT r.*, c.ip_address, p.product, p.cost, p.purchased, p.receipt FROM RefLinks r LEFT JOIN RefLink_Client c ON r.id = c.reflink_id LEFT JOIN RefLink_Product p ON r.id = p.reflink_id")
    List<RefLink> findAllRefLinksAndClientsAndProducts();


    @Insert("INSERT INTO RefLinks (title, URL, timestamp, transitions, registrations, purchases, pfu, pfk) " +
            "VALUES (#{title}, #{URL}, #{timestamp}, #{transitions}, #{registrations}, #{purchases}, #{pfu}, #{pfk})")
    void save(
            @Param("title") String title,
            @Param("URL") String URL,
            @Param("timestamp") Long timestamp,
            @Param("transitions") Long transitions,
            @Param("registrations") Long registrations,
            @Param("purchases") Long purchases,
            @Param("pfu") Double pfu,
            @Param("pfk") Double pfk
    );
    @Update("UPDATE RefLinks SET title=#{title}, URL=#{URL}, timestamp=#{timestamp}, " +
            "transitions=#{transitions}, registrations=#{registrations}, purchases=#{purchases}, " +
            "pfu=#{pfu}, pfk=#{pfk} WHERE id=#{id}")
    void update(RefLink refLink);
    @Select("SELECT reflink_id FROM RefLink_Client WHERE client.ip_address = #{ipAddress}")
    Long getRefLinkIdByIpAddress(@Param("ipAddress") String ipAddress);
    @Insert("INSERT INTO RefLink_Client (reflink_id, client) VALUES (#{refLinkId}, #{client})")
    void saveClientForRefLink(@Param("refLinkId") Long refLinkId, @Param("client") String client);
    @Select("SELECT client.ip_address FROM RefLink_Client client_link JOIN RefLinks ref_link ON client_link.reflink_id = ref_link.id WHERE ref_link.id = #{id}")
    List<Client> getClientsForRefLink(@Param("id") Long id);
    @Select("SELECT max(id) FROM RefLinks")
    Long findByMaxIdByDesc();


    @Select("SELECT r.*, c.ip_address, p.product, p.cost, p.purchased, p.receipt " +
            "FROM Reflinks r " +
            "LEFT JOIN RefLink_Client c ON r.id = c.reflink_id " +
            "LEFT JOIN RefLink_Product p ON r.id = p.reflink_id " +
            "WHERE r.id = #{id}")
    RefLink findById(@Param("id") Long id);


    @Delete("DELETE FROM Reflinks WHERE id = #{id}")
    void deleteById(@Param("id") Long id);
}
