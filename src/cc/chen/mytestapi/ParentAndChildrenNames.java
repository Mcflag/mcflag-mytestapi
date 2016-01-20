package cc.chen.mytestapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ParentAndChildrenNames {

	private static final String[] parentNames = new String[] { "Good libraries", "The New Apis", "New UI Apis",
			"Old Apis" };

	private static final String[] childrenNames0 = new String[] { "CoverFlowActivity" };

	private static final String[] childrenNames1 = new String[] { "AlarmManagerTest", "BasicGestureDetect",
			"BasicMultitouch", "BasicAndroidKeyStore", "BasicMediaDecoder", "BasicNetworkingState", "BluetoothChat",
			"MediaEffects", "MediaRecorderTest", "NetworkConnectTest", "StorageClient", "StorageProvider" };

	private static final String[] childrenNames2 = new String[] { "ActionBarCompatBasic",
			"ActionBarCompatListPopupMenu", "ActionBarCompatShareActionProvider", "ActionBarCompatStyled",
			"AdvancedImmersiveModeTest", "BasicImmersiveModeTest", "BasicAccessibilityTest", "BasicNotificationsTest",
			"BasicTransitionTest", "BorderlessButtonsTest", "CustomNotificationTest", "CustomTransitionTest",
			"CustomChoiceList", "DoneBarTest", "DrawableTintingTest", "HorizontalPaging", "ImageGridActivity",
			"ImmersiveModeTest", "SlidingTabsBasicTest", "SlidingTabsColorsTest", "SwipeRefreshLayoutBasicTest",
			"SwipeRefreshListFragmentTest", "SwipeRefreshMultipleViewsTest", "TextLinkifyTest", "TextSwitcherTest",
			"CardViewTest", "ClippingBasicTest", "FloatingActionButtonBasicTest", "NavigationDrawerTest",
			"RecyclerViewTest" };

	private static final String[] childrenNames3 = new String[] { "AccelerometerPlayActivity",
			"BasicGLSurfaceViewActivity", "TouchExampleActivity" };

	protected static List<String> getParentNames() {
		return Arrays.asList(parentNames);
	}

	protected static HashMap<String, List<String>> getMap() {
		HashMap<String, List<String>> tempMap = new HashMap<String, List<String>>();
		List<String> tempParentNames = getParentNames();
		ArrayList<List<String>> childrenNames = new ArrayList<List<String>>();
		childrenNames.add(Arrays.asList(childrenNames0));
		childrenNames.add(Arrays.asList(childrenNames1));
		childrenNames.add(Arrays.asList(childrenNames2));
		childrenNames.add(Arrays.asList(childrenNames3));

		for (int i = 0; i < tempParentNames.size(); i++) {
			if (childrenNames.size() > i && childrenNames.get(i) != null && childrenNames.size() > 0) {
				tempMap.put(tempParentNames.get(i), childrenNames.get(i));
			}
		}
		return tempMap;
	}
}
