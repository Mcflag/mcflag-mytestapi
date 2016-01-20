package cc.chen.mytestapi.test2;

import cc.chen.mytestapi.R;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ActionBarCompatListPopupMenu extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Set content view (which contains a PopupListFragment)
		setContentView(R.layout.actionbar_compat_listpopup_main);
	}
}
