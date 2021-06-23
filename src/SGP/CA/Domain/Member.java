package SGP.CA.Domain;

import java.util.Date;

public class Member {
    private int idMember;
    private String name;
    private String firstLastName;
    private String secondLastName;
    private Date dateOfBirth;
    private String phoneNumber;
    private String CURP;
    private String maximumStudyLevel;
    private String maximumStudyLevelInstitution;
    private int idLGAC;
    private int age;
    private String email;
    private String password;
    private int active;
    private int isResponsible;

    public static Member selectedMember;
    public static Member signedMember;

    public Member() {
    }

    public int getIdMember() {
        return idMember;
    }

    public void setIdMember(int idMember) {
        this.idMember = idMember;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstLastName() {
        return firstLastName;
    }

    public void setFirstLastName(String firstLastName) {
        this.firstLastName = firstLastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCURP() {
        return CURP;
    }

    public void setCURP(String CURP) {
        this.CURP = CURP;
    }

    public String getMaximumStudyLevel() {
        return maximumStudyLevel;
    }

    public void setMaximumStudyLevel(String maximumStudyLevel) {
        this.maximumStudyLevel = maximumStudyLevel;
    }

    public String getMaximumStudyLevelInstitution() {
        return maximumStudyLevelInstitution;
    }

    public void setMaximumStudyLevelInstitution(String maximumStudyLevelInstitution) {
        this.maximumStudyLevelInstitution = maximumStudyLevelInstitution;
    }

    public int getIdLGAC() {
        return idLGAC;
    }

    public void setIdLGAC(int idLGAC) {
        this.idLGAC = idLGAC;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getIsResponsible() {
        return isResponsible;
    }

    public void setIsResponsible(int isResponsible) {
        this.isResponsible = isResponsible;
    }

    public static Member getSelectedMember() {
        return selectedMember;
    }

    public static void setSelectedMember(Member selectedMember) {
        Member.selectedMember = selectedMember;
    }
}
