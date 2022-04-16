package com.ddarahakit.backend.course;


import com.ddarahakit.backend.course.model.PostCourseReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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


}
