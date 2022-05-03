package com.ddarahakit.backend.course.lesson;

import com.ddarahakit.backend.config.BaseException;
import com.ddarahakit.backend.course.chapter.ChapterDao;
import com.ddarahakit.backend.course.chapter.model.PostChapterReq;
import com.ddarahakit.backend.course.chapter.model.PostChapterRes;
import com.ddarahakit.backend.course.lesson.model.PostLessonReq;
import com.ddarahakit.backend.course.lesson.model.PostLessonRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.ddarahakit.backend.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class LessonService {

    @Autowired
    private LessonDao lessonDao;

    public PostLessonRes createLesson(PostLessonReq postLessonReq) throws BaseException {
        try {
            return lessonDao.createLesson(postLessonReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
