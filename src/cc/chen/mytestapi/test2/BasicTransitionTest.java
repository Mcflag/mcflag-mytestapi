/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cc.chen.mytestapi.test2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.transition.Scene;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ViewAnimator;

import cc.chen.mytestapi.BaseActivity;
import cc.chen.mytestapi.R;

/**
 * A simple launcher activity containing a summary sample description, sample
 * log and a custom {@link android.support.v4.app.Fragment} which can display a
 * view.
 * <p>
 * For devices with displays with a width of 720dp or greater, the sample log is
 * always visible, on other devices it's visibility is controlled by an item on
 * the Action Bar.
 */
@SuppressLint("NewApi")
public class BasicTransitionTest extends BaseActivity {

	public static final String TAG = "MainActivity";

	// Whether the Log Fragment is currently shown
	private boolean mLogShown;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.basic_transition_test_main);

		if (savedInstanceState == null) {
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			BasicTransitionFragment fragment = new BasicTransitionFragment();
			transaction.replace(R.id.sample_content_fragment, fragment);
			transaction.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.basic_transition_test_menu, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem logToggle = menu.findItem(R.id.menu_toggle_log);
		logToggle.setVisible(findViewById(R.id.sample_output) instanceof ViewAnimator);
		logToggle.setTitle(mLogShown ? R.string.sample_hide_log : R.string.sample_show_log);

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_toggle_log:
			mLogShown = !mLogShown;
			ViewAnimator output = (ViewAnimator) findViewById(R.id.sample_output);
			if (mLogShown) {
				output.setDisplayedChild(1);
			} else {
				output.setDisplayedChild(0);
			}
			supportInvalidateOptionsMenu();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public class BasicTransitionFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

		// We transition between these Scenes
		private Scene mScene1;
		private Scene mScene2;
		private Scene mScene3;

		/** A custom TransitionManager */
		private TransitionManager mTransitionManagerForScene3;

		/**
		 * Transitions take place in this ViewGroup. We retain this for the
		 * dynamic transition on scene 4.
		 */
		private ViewGroup mSceneRoot;

		public BasicTransitionFragment newInstance() {
			return new BasicTransitionFragment();
		}

		public BasicTransitionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.basic_transition_test_fragment_basic_transition, container, false);
			assert view != null;
			RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.select_scene);
			radioGroup.setOnCheckedChangeListener(this);
			mSceneRoot = (ViewGroup) view.findViewById(R.id.scene_root);

			// A Scene can be instantiated from a live view hierarchy.
			mScene1 = new Scene(mSceneRoot, (ViewGroup) mSceneRoot.findViewById(R.id.container));

			// You can also inflate a generate a Scene from a layout resource
			// file.
			mScene2 = Scene.getSceneForLayout(mSceneRoot, R.layout.basic_transition_test_scene2, getActivity());

			// Another scene from a layout resource file.
			mScene3 = Scene.getSceneForLayout(mSceneRoot, R.layout.basic_transition_test_scene3, getActivity());

			// We create a custom TransitionManager for Scene 3, in which
			// ChangeBounds and Fade
			// take place at the same time.
			mTransitionManagerForScene3 = TransitionInflater.from(getActivity()).inflateTransitionManager(
					R.transition.scene3_transition_manager, mSceneRoot);

			return view;
		}

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.select_scene_1: {

				// You can start an automatic transition with
				// TransitionManager.go().
				TransitionManager.go(mScene1);

				break;
			}
			case R.id.select_scene_2: {
				TransitionManager.go(mScene2);
				break;
			}
			case R.id.select_scene_3: {

				// You can also start a transition with a custom
				// TransitionManager.
				mTransitionManagerForScene3.transitionTo(mScene3);

				break;
			}
			case R.id.select_scene_4: {

				// Alternatively, transition can be invoked dynamically without
				// a Scene.
				// For this, we first call
				// TransitionManager.beginDelayedTransition().
				TransitionManager.beginDelayedTransition(mSceneRoot);
				// Then, we can just change view properties as usual.
				View square = mSceneRoot.findViewById(R.id.transition_square);
				ViewGroup.LayoutParams params = square.getLayoutParams();
				int newSize = getResources().getDimensionPixelSize(R.dimen.square_size_expanded);
				params.width = newSize;
				params.height = newSize;
				square.setLayoutParams(params);

				break;
			}
			}
		}

	}
}
