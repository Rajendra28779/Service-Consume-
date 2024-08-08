package com.bskyserviceconsume.controller;

import com.bskyserviceconsume.model.Txntransactiondetail;
import com.bskyserviceconsume.repository.TxntransactiondetailRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Project : BSKY Service Consume
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 08/05/2023 - 2:15 PM
 */
@Controller
public class MainController {

    @Autowired
    private Logger logger;

    @Autowired
    private TxntransactiondetailRepository txntransactiondetailRepository;

    @ResponseBody
    @GetMapping(value = "/success")
    public String index(Sort sort) {
        logger.info("Inside Success Method of MainController.");
        int pageSize = 10;
        int pageNumber = 1;
        try {
            Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
            Page<Txntransactiondetail> txntransactiondetailPage = txntransactiondetailRepository.findAll(pageable);
            List<Txntransactiondetail> txntransactiondetailList = txntransactiondetailPage.getContent();
            for (Txntransactiondetail txntransactiondetail : txntransactiondetailList) {
                System.out.println(txntransactiondetail);
            }
        }catch (Exception e) {
            logger.error("Error in Success Method of MainController" + e.getMessage());
        }
        return "Success";
    }
}
