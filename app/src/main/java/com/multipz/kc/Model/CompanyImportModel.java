package com.multipz.kc.Model;

/**
 * Created by Admin on 14-09-2017.
 */

public class CompanyImportModel {
    private String weight_unit, weight_bags, price_per_ton, total_price_per_ton;


    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    private String cdate;

    public String getPrice_per_ton() {
        return price_per_ton;
    }

    public void setPrice_per_ton(String price_per_ton) {
        this.price_per_ton = price_per_ton;
    }

    public String getTotal_price_per_ton() {
        return total_price_per_ton;
    }

    public void setTotal_price_per_ton(String total_price_per_ton) {
        this.total_price_per_ton = total_price_per_ton;
    }

    public String getWeight_unit() {
        return weight_unit;
    }

    public void setWeight_unit(String weight_unit) {
        this.weight_unit = weight_unit;
    }

    public String getWeight_bags() {
        return weight_bags;
    }

    public void setWeight_bags(String weight_bags) {
        this.weight_bags = weight_bags;
    }

    public String getComp_import_id() {
        return comp_import_id;
    }

    public void setComp_import_id(String comp_import_id) {
        this.comp_import_id = comp_import_id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(String material_id) {
        this.material_id = material_id;
    }

    public String getChallan_no() {
        return challan_no;
    }

    public void setChallan_no(String challan_no) {
        this.challan_no = challan_no;
    }

    public String getChallan_image() {
        return challan_image;
    }

    public void setChallan_image(String challan_image) {
        this.challan_image = challan_image;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getModified_date() {
        return modified_date;
    }

    public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }

    public String getIs_status() {
        return is_status;
    }

    public void setIs_status(String is_status) {
        this.is_status = is_status;
    }

    public String getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSide_sort_name() {
        return side_sort_name;
    }

    public void setSide_sort_name(String side_sort_name) {
        this.side_sort_name = side_sort_name;
    }

    public String getMaterial_type() {
        return material_type;
    }

    public void setMaterial_type(String material_type) {
        this.material_type = material_type;
    }

    String comp_import_id, company_id, project_id, material_id, challan_no, challan_image, amount, user_id, detail, created_date, modified_date, is_status, is_delete, companyName, side_sort_name, material_type;
}
