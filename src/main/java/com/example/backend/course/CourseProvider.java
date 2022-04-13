package com.example.backend.course;


import com.example.backend.config.BaseException;
import com.example.backend.course.model.GetCourseRes;
import com.example.backend.course.model.PostCourseReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.backend.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.backend.config.BaseResponseStatus.RESPONSE_NULL_ERROR_BY_IDX;


@Service
public class CourseProvider {
    @Autowired
    private CourseDao courseDao;

    public boolean isPreExistsCourse(PostCourseReq postCourseReq) {
        if (courseDao.isPreExistsCourse(postCourseReq)) {
            return true;
        }
        return false;
    }

    public GetCourseRes getCourse(Integer idx) throws BaseException {
        if (courseDao.isNotExistedCourse(idx)) {
            throw new BaseException(RESPONSE_NULL_ERROR_BY_IDX);
        }

        try {
            GetCourseRes getCourseRes = courseDao.getCourse(idx);

            return getCourseRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
