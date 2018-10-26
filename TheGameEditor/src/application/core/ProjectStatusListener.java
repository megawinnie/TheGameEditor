package application.core;

public interface ProjectStatusListener {
	
	void onSetProject();
	void onRiseProjectSavedFlag();
	void onFallProjectSavedFlag();
	
}
