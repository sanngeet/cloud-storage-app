package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM notes WHERE userid=#{userId}")
    public List<Note> getNotes(Integer userId);

    @Update("UPDATE notes SET notetitle=#{noteTitle}, notedescription=#{noteDescription} WHERE noteid=#{noteId}")
    public Integer updateNote(Note note);

    @Insert("Insert INTO notes (notetitle, notedescription, userid) VALUES (#{noteTitle}, #{noteDescription}, " +
            "#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    public Integer insert(Note note);

    @Delete("DELETE FROM notes WHERE noteid=#{noteId}")
    public Integer delete(Integer noteId);
}
