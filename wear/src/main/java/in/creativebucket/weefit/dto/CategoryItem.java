package in.creativebucket.weefit.dto;

/**
 * Created by Surajl on 6/27/2015.
 */
public class CategoryItem {


    public int iconRes;
    public String categoryName;

    public CategoryItem(int iconRes, String categoryName) {
        this.iconRes = iconRes;
        this.categoryName = categoryName;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
