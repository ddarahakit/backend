package com.ddarahakit.backend.chapter;


import com.ddarahakit.backend.chapter.model.PostChapterReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ChapterProvider {
    @Autowired
    private ChapterDao courseDao;

    public boolean isPreExistsChapter(PostChapterReq postChapterReq) {
        if (courseDao.isPreExistsChapter(postChapterReq)) {
            return true;
        }
        return false;
    }


}
