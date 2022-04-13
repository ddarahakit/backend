package com.example.backend.course;

import com.example.backend.config.BaseException;
import com.example.backend.config.BaseResponseStatus;
import com.example.backend.course.model.PostCourseReq;
import com.example.backend.course.model.PostCourseRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.backend.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.backend.config.BaseResponseStatus.POST_COURSES_PRE_EXIST_COURSE;

@Service
public class CourseService {

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private CourseProvider courseProvider;


    public PostCourseRes createCourse(PostCourseReq postCourseReq) throws BaseException {
        //이름 중복
        if (courseProvider.isPreExistsCourse(postCourseReq)) {
            throw new BaseException(POST_COURSES_PRE_EXIST_COURSE);
        }

        try {
            return courseDao.createCourse(postCourseReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
