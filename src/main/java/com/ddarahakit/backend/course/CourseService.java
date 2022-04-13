package com.ddarahakit.backend.course;

import com.ddarahakit.backend.config.BaseException;
import com.ddarahakit.backend.course.model.PostCourseReq;
import com.ddarahakit.backend.course.model.PostCourseRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.ddarahakit.backend.config.BaseResponseStatus.DATABASE_ERROR;
import static com.ddarahakit.backend.config.BaseResponseStatus.POST_COURSES_PRE_EXIST_COURSE;

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
