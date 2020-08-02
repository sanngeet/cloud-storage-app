package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM credentials WHERE userid=#{userId}")
    public List<Credentials> getAll(int userId);

    @Insert("INSERT INTO credentials (url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, " + "#{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    public int add(Credentials credentials);

    @Delete("DELETE FROM credentials WHERE credentialid=#{credentialId}")
    public int delete(Integer credentialId);

    @Update("UPDATE credentials SET url=#{url}, username=#{username}, password=#{password}  WHERE " + "credentialid=#{credentialId}")
    public int update(Credentials credentials);

    @Select("SELECT * FROM credentials WHERE credentialid=#{credentialId}")
    public Credentials getCredentialById(Integer credentialId);
}
