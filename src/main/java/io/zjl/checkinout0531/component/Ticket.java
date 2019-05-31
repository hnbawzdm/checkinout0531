package io.zjl.checkinout0531.component;

import org.springframework.stereotype.Component;

/**
 * @Author:meng
 * @Date:2019/5/301057
 * @description
 */
@Component
public class Ticket{

    private String ticket;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
