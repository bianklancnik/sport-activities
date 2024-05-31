package sports.activities.application.model;

import jakarta.persistence.*;

@Entity
@Table(name = "activities")
public class Activity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "assessment")
	private int assessment;

	@Column(name = "username")
	private String username;

	public Activity() {

	}

	public Activity(String name, String description, int assessment, String username) {
		this.name = name;
		this.description = description;
		this.assessment = assessment;
		this.username = username;
	}

	public long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAssessment() {
		return this.assessment;
	}

	public void setAssessment(int assessment) {
		this.assessment = assessment;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
