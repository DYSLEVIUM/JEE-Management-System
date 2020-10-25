package tableModels;

public class meritListTableModel {
    int rank,totalMarks;
    String rollnumber, studentName,category;
    int maths, physics, chemistry;

    public meritListTableModel(int rank, String rollnumber, String studentName, String category, int totalMarks, int maths, int physics,int chemistry){
        this.rank=rank;
        this.rollnumber = rollnumber;
        this.studentName = studentName;
        this.category = category;
        this.totalMarks = totalMarks;
        this.maths = maths;
        this.physics = physics;
        this.chemistry = chemistry;
    }

    public int getRank(){
        return this.rank;
    }

    public int getTotalMarks(){
        return this.totalMarks;
    }

    public int getMaths(){
        return this.maths;
    }

    public int getPhysics(){
        return this.physics;
    }

    public int getChemistry(){
        return this.chemistry;
    }

    public String getRollnumber(){
        return this.rollnumber;
    }

    public String getStudentName(){
        return this.studentName;
    }

    public String getCategory(){
        return this.category;
    }

    public void setRank(int rank){
        this.rank = rank;
    }

    public void setTotalMarks(int totalMarks){
        this.totalMarks = totalMarks;
    }

    public void setMaths(int maths){
        this.maths = maths;
    }

    public void setPhysics(int physics){
        this.physics = physics;
    }

    public void setChemistry(int chemistry){
        this.chemistry = chemistry;
    }

    public void setRollnumber(String rollnumber){
        this.rollnumber = rollnumber;
    }

    public void setStudentName(String studentName){
        this.studentName = studentName;
    }

    public void setCategory(String category){
        this.category = category;
    }

}
