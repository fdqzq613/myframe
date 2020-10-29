package ${package.queryVo};
import javax.validation.constraints.NotBlank;

/**
 * <p>
 * ${table.comment}查询QueryVo
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
public class ${entity}UpdateVo {
	@NotBlank(message = "id不能为空")
	private String id;
   
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
