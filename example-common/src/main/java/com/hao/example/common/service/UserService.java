package com.hao.example.common.service;

import com.hao.example.common.model.User;

public interface UserService {
    /**
     * get user
     *
     * @param user user
     * @return {@code User}
     * @author jiangyihao
     * @version 5.0.0
     * @date 2024/05/01
     */
    User getUser(User user);

    /**
     * （测试）
     *
     * @return short
     * @author haoge
     * @version 5.0.0
     * @date 2024/05/02
     */
    default short getNumber() {
        return 1;
    }
}
