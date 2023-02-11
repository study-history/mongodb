package study.nosql.mongodb.common.documents.category.document;

import org.springframework.data.mongodb.core.mapping.Document;
import study.nosql.mongodb.business.domain.category.entity.CategoryGroup;
import study.nosql.mongodb.business.domain.category.entity.ConcreteCategory;
import study.nosql.mongodb.business.domain.category.entity.SubCategory;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Objects;

@Document(collation = "category")
public class CategoryDocument {

    @Id
    private Long _id;

    @Column
    private Long categoryGroupId;

    @Column
    private SubCategoryDocument subCategory;

    @Column
    private String name;

    /**
     * @Nullary-Constructor. MongoDB 기본 생성자로 category document 패키지 외부에서 호출하지 말 것.
     */
    private CategoryDocument() {
    }

    public CategoryDocument(ConcreteCategory concreteCategory) {
        SubCategory subCategory = concreteCategory.getSubCategory();
        CategoryGroup categoryGroup = subCategory.getCategoryGroup();

        this.categoryGroupId = categoryGroup.getCategoryGroupId();
        this.name = categoryGroup.getName();
        this.subCategory = new SubCategoryDocument(subCategory, concreteCategory);
    }

    public Long get_id() {
        return _id;
    }

    public Long getCategoryGroupId() {
        return categoryGroupId;
    }

    public SubCategoryDocument getSubCategory() {
        return subCategory;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryDocument that)) return false;
        return getCategoryGroupId().equals(that.getCategoryGroupId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCategoryGroupId());
    }

    @Override
    public String toString() {
        return """
                {
                    "_id": %s,
                    "categoryGroupId": %s,
                    "name": %s,
                    "subCategory": %s
                }
                """.formatted(
                _id,
                categoryGroupId,
                name,
                subCategory
        );
    }
}
