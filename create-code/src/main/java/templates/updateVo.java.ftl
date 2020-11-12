package ${package.queryVo};
import javax.validation.constraints.NotBlank;
import ${package.Entity}.${entity};

/**
 * <p>
 * ${table.comment}更新UpdateVo
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
public class ${entity}UpdateVo extends ${entity}{
	@NotBlank(message = "id不能为空")
	private String id;
   
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
