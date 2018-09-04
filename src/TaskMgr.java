
public class TaskMgr {
	private TaskMgr_View taskmgr_view;
	public TaskMgr(Main_View view){
		taskmgr_view = new TaskMgr_View(view);
	}
	public TaskMgr_View getTaskMgr_View(){
		return taskmgr_view;
	}
}
