package com.vodacom.er.gui.service;


import com.vodacom.er.gui.entity.Sub_Functions;
import com.vodacom.er.gui.interceptor.LoggingInterceptor;
import com.vodacom.er.gui.repository.EsSubFunctionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EsSubFunctionsService {
    @Autowired
    private EsSubFunctionsRepository esSubFunctionsRepository;
    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    public Sub_Functions saveSubFunction(Sub_Functions _sub_functions) {
        logger.info("EsSubFunctionsService : saveSubFunction() Method Called : Successfully ");
        return esSubFunctionsRepository.save(_sub_functions);
    }

    public List<Sub_Functions> allSubFunction() {
        logger.info("EsSubFunctionsService : allSubFunction() Method Called : Successfully ");
        return esSubFunctionsRepository.findAll();
    }

    public Optional<Sub_Functions> getSubFunctionById(Integer id) {
        logger.info("EsSubFunctionsService : getSubFunctionById() Method Called : Successfully ");
        return esSubFunctionsRepository.findById(id);
    }
    public Optional<Sub_Functions> getActualSubFunctionById(Integer id) {
        logger.info("EsSubFunctionsService : getActualSubFunctionById() Method Called : Successfully ");
        return esSubFunctionsRepository.findById(id);
    }


    public Optional<Sub_Functions> updateEsSubFunction(Sub_Functions _sub_functions) {
        logger.info("EsSubFunctionsService : updateEsSubFunction() Method Called :  ");
        Optional<Sub_Functions> stu = esSubFunctionsRepository.findById(_sub_functions.getSeq());
        Sub_Functions s = stu.get();
        s.setSeq(_sub_functions.getSeq());
        s.setName(_sub_functions.getName());
        s.setDescription(_sub_functions.getDescription());
        s.setActive_yn(_sub_functions.getActive_yn());
        s.setModified_date(_sub_functions.getModified_date());
        s.setModified_by(_sub_functions.getModified_by());
        esSubFunctionsRepository.save(s);
        logger.info("EsSubFunctionsService : updateEsSubFunction() Method Called : Successfully ");
        return esSubFunctionsRepository.findById(_sub_functions.getSeq());
    }

    public void  deleteEsSubFunctionById(Integer id) {
        logger.info("EsSubFunctionsService : deleteEsSubFunctionById() Method Called : Successfully ");
        esSubFunctionsRepository.deleteById(id);
    }

}
