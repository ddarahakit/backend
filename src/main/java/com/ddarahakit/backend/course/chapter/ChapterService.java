package com.ddarahakit.backend.course.chapter;

import com.ddarahakit.backend.course.chapter.model.PostChapterReq;
import com.ddarahakit.backend.course.chapter.model.PostChapterRes;
import com.ddarahakit.backend.config.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.ddarahakit.backend.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class ChapterService {

    @Autowired
    private ChapterDao chapterDao;

    public PostChapterRes createChapter(PostChapterReq postChapterReq) throws BaseException {
        try {
            return chapterDao.createChapter(postChapterReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
