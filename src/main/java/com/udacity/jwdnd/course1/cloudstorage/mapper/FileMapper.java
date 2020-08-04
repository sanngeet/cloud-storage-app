package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM files WHERE userid=#{userId}")
    public List<File> getFiles(Integer userId);

    @Delete("DELETE FROM files WHERE fileId=#{fileId}")
    public Integer deleteFile(Integer fileId);

    @Select("SELECT * FROM files WHERE fileId=#{fileId}")
    public File getOne(Integer fileId);

    @Insert("INSERT INTO files (filename, contenttype, filesize, userid, filedata) VALUES(#{fileName}, " +
            "#{contentType}, " +
            "#{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    public Integer insertFile(File file);

    @Select("SELECT * FROM files WHERE filename=#{fileName}")
    public File filenameExists(String fileName);
}
