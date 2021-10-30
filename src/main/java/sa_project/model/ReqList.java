package sa_project.model;

import java.util.ArrayList;

public class ReqList {
    private ArrayList<ReqForm> reqList;

    public ReqList() {
        reqList = new ArrayList<>();
    }

    public void addReqList(ReqForm req){reqList.add(req);}

    public ArrayList<ReqForm> toList(){return reqList;}

    public void setStatus(ReqForm selectreq,String status){
        System.out.println("Enter");
        for(ReqForm rq : reqList){
            if(rq.getRqNumber() == selectreq.getRqNumber()){
                rq.setRqStatus(status);
                System.out.println(reqList.get(3).getRqStatus());
            }
        }
    }
}
