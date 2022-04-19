package com.ddarahakit.backend.question;

import com.ddarahakit.backend.config.BaseException;
import com.ddarahakit.backend.question.model.PostQuestionReq;
import com.ddarahakit.backend.question.model.PostQuestionRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import static com.ddarahakit.backend.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    public PostQuestionRes createQuestion(@RequestBody PostQuestionReq postQuestionReq) throws BaseException {
        try {
            return questionDao.createQuestion(postQuestionReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}