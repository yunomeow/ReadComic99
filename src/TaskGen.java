
public class TaskGen {
	private TaskGen_View taskgen_view;
	public TaskGen(Main_View view,TaskMgr taskmgr){
		taskgen_view = new TaskGen_View(view,taskmgr);
	}
	public TaskGen_View getView(){
		return taskgen_view;
	}
}
