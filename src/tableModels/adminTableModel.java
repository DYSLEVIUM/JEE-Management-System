package tableModels;

public class adminTableModel {
    String rollnumber, studentName,category;
    int maths, physics, chemistry;

    public adminTableModel(String rollnumber, String studentName, String category, int maths, int physics, int chemistry){
        this.rollnumber = rollnumber;
        this.studentName = studentName;
        this.category = category;
        this.maths = maths;
        this.physics = physics;
        this.chemistry = chemistry;
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

    public int getMaths(){
        return this.maths;
    }

    public int getPhysics(){
        return this.physics;
    }

    public int getChemistry(){
        return this.chemistry;
    }

    public void setRollnumber(String roll){
        this.rollnumber = roll;
    }

    public void setStudentName(String studentName){
        this.studentName = studentName;
    }
    public void setCategory(String category){
        this.category = category;
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

}
