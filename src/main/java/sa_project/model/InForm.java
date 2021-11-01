package sa_project.model;

public class InForm {
    private String inNo;
    private String inDate;
    private String prNo;
    private String empId;
    private String empName;

    public InForm(String inNo, String inDate, String prNo, String empId) {
        this.inNo = inNo;
        this.inDate = inDate;
        this.prNo = prNo;
        this.empId = empId;
//        this.empName = empName;
    }

    public String getInNo() {
        return inNo;
    }

    public void setInNo(String inNo) {
        this.inNo = inNo;
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public String getPrNo() {
        return prNo;
    }

    public void setPrNo(String prNo) {
        this.prNo = prNo;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }
}
