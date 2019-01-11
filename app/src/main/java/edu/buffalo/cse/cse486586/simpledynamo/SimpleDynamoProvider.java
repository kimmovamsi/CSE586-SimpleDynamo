package edu.buffalo.cse.cse486586.simpledynamo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.util.Log;

public class SimpleDynamoProvider extends ContentProvider {


	static final String all_avds[] = {"5554", "5556", "5558", "5560", "5562"};
	ArrayList<String> sabka_hashed_ids = new ArrayList<String>();
	ArrayList<String> sorted_hashed_ids = new ArrayList<String>();
	ArrayList<majormc> the_real_slim_shady = new ArrayList<majormc>();
	HashMap<String, String> star_storer = new HashMap<String, String>();
	HashMap<String, String> someone_dead_ka_insert = new HashMap<String, String>();
	final int SERVER_PORT = 10000;
	String myhashedavdid;
	String myavd, myport;
	String sucsuc, suc, pre, parent;

	class majormc{
		String key;
		String value;
		public majormc(String key, String value){
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub


		String hashedkey = null, hashedpre = null;
		try{
			hashedkey = genHash(selection);
			hashedpre = genHash(pre);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		if (hashedkey.compareTo(myhashedavdid) <= 0 && hashedkey.compareTo(hashedpre) > 0) {
			getContext().deleteFile(selection);
			String temp = "deletethis" + "~" + selection + "~" + suc;
			new ClientTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, temp);
			temp = "deletethis" + "~" + selection + "~" + sucsuc;
			new ClientTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, temp);
		} else {

			ArrayList<String> hashed_avd_ids = new ArrayList<String>();
			for(int i = 0; i < 5; i++){
				try {
					hashed_avd_ids.add(genHash(all_avds[i]));
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
			}

			hashed_avd_ids.add(hashedkey);
			Collections.sort(hashed_avd_ids);

			int hashedkey_ka_index = hashed_avd_ids.indexOf(hashedkey);
			if (hashedkey_ka_index > 0 && hashedkey_ka_index < 5){
				String forward = all_avds[sabka_hashed_ids.indexOf(hashed_avd_ids.get(hashedkey_ka_index+1))];
				String temp = "deletethis" + "~" + selection + "~" + forward;
				new ClientTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, temp);

				int x = (hashedkey_ka_index + 2) % hashed_avd_ids.size();
				forward = all_avds[sabka_hashed_ids.indexOf(hashed_avd_ids.get(x))];
				temp = "deletethis" + "~" + selection + "~" + forward;
				new ClientTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, temp);

				x = (hashedkey_ka_index + 3) % hashed_avd_ids.size();
				forward = all_avds[sabka_hashed_ids.indexOf(hashed_avd_ids.get(x))];
				temp = "deletethis" + "~" + selection + "~" + forward;
				new ClientTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, temp);

				Log.v("kiskatoh delete", selection);
			} else {
				String temp = "deletethis" + "~" + selection + "~" + parent;
				new ClientTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, temp);
				temp = "deletethis" + "~" + selection + "~" + "5556";
				new ClientTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, temp);
				temp = "deletethis" + "~" + selection + "~" + "5554";
				new ClientTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, temp);

				Log.v("parent delete", selection);
			}

			hashed_avd_ids.clear();


		}


		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub


		String hashedkey = null, hashedpre = null;
		try{
			hashedkey = genHash(values.getAsString("key"));
			hashedpre = genHash(pre);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		System.out.println("newinsert");

		if (hashedkey.compareTo(myhashedavdid) <= 0 && hashedkey.compareTo(hashedpre) > 0) {
			FileOutputStream fos;
			//https://developer.android.com/reference/java/io/FileOutputStream.html

			try {
				fos = getContext().openFileOutput(values.getAsString("key"), Context.MODE_PRIVATE);
				//https://developer.android.com/reference/android/content/Context.html#openFileOutput(java.lang.String,%20int)
				fos.write(values.getAsString("value").getBytes());
				fos.close();
			} catch (Exception e) {
				//Log.e("Error", "File write failed");
			}

			Log.v("kardiya insert", values.toString());


			String temp = "store_kar_isko" + "~" + values.getAsString("key") + "~" + values.getAsString("value") + "~" + suc;
			new ClientTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, temp);
			temp = "store_kar_isko" + "~" + values.getAsString("key") + "~" + values.getAsString("value") + "~" + sucsuc;
			new ClientTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, temp);
		} else{
			ArrayList<String> hashed_avd_ids = new ArrayList<String>();
			for(int i = 0; i < 5; i++){
				try {
					hashed_avd_ids.add(genHash(all_avds[i]));
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
			}

			hashed_avd_ids.add(hashedkey);
			Collections.sort(hashed_avd_ids);

			int hashedkey_ka_index = hashed_avd_ids.indexOf(hashedkey);
			if (hashedkey_ka_index > 0 && hashedkey_ka_index < 5){
				//System.out.println("error arai bro yaha 1 - " + " " + hashedkey_ka_index+1 + " " + hashed_avd_ids.get(hashedkey_ka_index+1) + " " +  sabka_hashed_ids.indexOf(hashed_avd_ids.get(hashedkey_ka_index+1)));
				String forward = all_avds[sabka_hashed_ids.indexOf(hashed_avd_ids.get(hashedkey_ka_index+1))];
				String temp = "store_kar_isko" + "~" + values.getAsString("key") + "~" + values.getAsString("value") + "~" + forward;
				Log.v("kiskatoh insert", values.toString() + "------------" + forward);
				new ClientTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, temp);


				int x = (hashedkey_ka_index + 2) % hashed_avd_ids.size();
				forward = all_avds[sabka_hashed_ids.indexOf(hashed_avd_ids.get(x))];
				temp = "store_kar_isko" + "~" + values.getAsString("key") + "~" + values.getAsString("value") + "~" + forward;
				new ClientTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, temp);
				x = (hashedkey_ka_index + 3) % hashed_avd_ids.size();
				forward = all_avds[sabka_hashed_ids.indexOf(hashed_avd_ids.get(x))];
				temp = "store_kar_isko" + "~" + values.getAsString("key") + "~" + values.getAsString("value") + "~" + forward;
				new ClientTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, temp);


			} else {
				if (myavd.equals(parent)){
					FileOutputStream fos;
					//https://developer.android.com/reference/java/io/FileOutputStream.html

					try {
						fos = getContext().openFileOutput(values.getAsString("key"), Context.MODE_PRIVATE);
						//https://developer.android.com/reference/android/content/Context.html#openFileOutput(java.lang.String,%20int)
						fos.write(values.getAsString("value").getBytes());
						fos.close();
					} catch (Exception e) {
						//Log.e("Error", "File write failed");
					}

					Log.v("me parent, so insert", values.toString());
					String temp = "store_kar_isko" + "~" + values.getAsString("key") + "~" + values.getAsString("value") + "~" + suc;
					new ClientTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, temp);
					temp = "store_kar_isko" + "~" + values.getAsString("key") + "~" + values.getAsString("value") + "~" + sucsuc;
					new ClientTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, temp);
				} else {
					String temp = "store_kar_isko" + "~" + values.getAsString("key") + "~" + values.getAsString("value") + "~" + parent;
					new ClientTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, temp);
					Log.v("parent insert", values.toString());

					temp = "store_kar_isko" + "~" + values.getAsString("key") + "~" + values.getAsString("value") + "~" + "5556";
					new ClientTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, temp);
					temp = "store_kar_isko" + "~" + values.getAsString("key") + "~" + values.getAsString("value") + "~" + "5554";
					new ClientTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, temp);





				}
			}


			hashed_avd_ids.clear();

		}





		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub

		ArrayList<String> hashed_avd_ids = new ArrayList<String>();

		for(int i = 0; i < 5; i++){
			try {
				sabka_hashed_ids.add(genHash(all_avds[i]));
				hashed_avd_ids.add(genHash(all_avds[i]));
				sorted_hashed_ids.add(genHash(all_avds[i]));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}



		TelephonyManager tel = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
		myavd = tel.getLine1Number().substring(tel.getLine1Number().length() - 4);
		myport = String.valueOf((Integer.parseInt(myavd) * 2));

		Collections.sort(hashed_avd_ids);
		Collections.sort(sorted_hashed_ids);
		System.out.println("sorted hashed avds - " + hashed_avd_ids);
		try{
			myhashedavdid = genHash(myavd);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		int index = hashed_avd_ids.indexOf(myhashedavdid);
		System.out.println("myavd - " + myavd);
		System.out.println("index of myavd - " + index);
		if (index > 0 &&  index <= 2){
			suc = all_avds[sabka_hashed_ids.indexOf(hashed_avd_ids.get(index+1))];
			sucsuc = all_avds[sabka_hashed_ids.indexOf(hashed_avd_ids.get(index+2))];
			pre = all_avds[sabka_hashed_ids.indexOf(hashed_avd_ids.get(index-1))];
		} else if (index == 0){
			suc = all_avds[sabka_hashed_ids.indexOf(hashed_avd_ids.get(index+1))];
			sucsuc = all_avds[sabka_hashed_ids.indexOf(hashed_avd_ids.get(index+2))];
			pre = all_avds[sabka_hashed_ids.indexOf(hashed_avd_ids.get(4))];
		} else if (index == 3){
			suc = all_avds[sabka_hashed_ids.indexOf(hashed_avd_ids.get(index+1))];
			sucsuc = all_avds[sabka_hashed_ids.indexOf(hashed_avd_ids.get(0))];
			pre = all_avds[sabka_hashed_ids.indexOf(hashed_avd_ids.get(index-1))];
		} else if (index == 4){
			suc = all_avds[sabka_hashed_ids.indexOf(hashed_avd_ids.get(0))];
			sucsuc = all_avds[sabka_hashed_ids.indexOf(hashed_avd_ids.get(1))];
			pre = all_avds[sabka_hashed_ids.indexOf(hashed_avd_ids.get(index-1))];
		}

		parent = all_avds[sabka_hashed_ids.indexOf(hashed_avd_ids.get(0))];
		System.out.println(suc + "!!!!" + sucsuc + "!!!!" + pre + "!!!!" + parent);

		hashed_avd_ids.clear();
		try {
			//ServerSocket serverSocket = new ServerSocket();
			//serverSocket.setReuseAddress(true);
			//serverSocket.bind(new InetSocketAddress(SERVER_PORT));
			ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
			new ServerTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, serverSocket);
			//from previous PA's
		} catch (IOException e) {
			Log.e("Error", "Can't create a ServerSocket");
		}


		String imalive = "imalive" + "~" + myavd;
		new ClientTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, imalive);
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub


		String collected = null;
		FileInputStream fis = null;
		MatrixCursor mc = new MatrixCursor(new String[] {"key", "value"});

		if (selection.equals("*")){
			System.out.println("Hello");
			String[] path = getContext().getApplicationContext().fileList();

			for (int i = 0; i < path.length; i++){
				try {
					fis = getContext().openFileInput(path[i]);
					byte[] input = new byte[fis.available()];
					while (fis.read(input) != -1) {
						collected = new String(input);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				star_storer.put(path[i], collected);
				//mc.addRow(new Object[] {path[i], collected});
				System.out.println(path[i] + "  " + collected);
			}

			String popeye = "givemeeverthing" + "~" + myavd;
			String testing = null;

			try {
				testing = new ClientTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, popeye).get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}

			if (testing.equals("done")){

				for (String name: star_storer.keySet()){
					mc.addRow(new Object[] {name, star_storer.get(name)});
				}
			}
		} else if(selection.equals("@")){
			System.out.println("Hola");
			String[] path = getContext().getApplicationContext().fileList();

			for (int i = 0; i < path.length; i++){
				try {
					fis = getContext().openFileInput(path[i]);
					byte[] input = new byte[fis.available()];
					while (fis.read(input) != -1) {
						collected = new String(input);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				mc.addRow(new Object[] {path[i], collected});
				System.out.println(path[i] + "  " + collected);
			}
		} else {
			String hashedkey = null, hashedpre = null;
			try{
				hashedkey = genHash(selection);
				hashedpre = genHash(pre);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			System.out.println("konsa chahiye tereko? - " + selection);
			if (hashedkey.compareTo(myhashedavdid) <= 0 && hashedkey.compareTo(hashedpre) > 0) {
				try {
					fis = getContext().openFileInput(selection);
					byte[] input = new byte[fis.available()];
					while (fis.read(input) != -1) {
						collected = new String(input);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				System.out.println("mere pas hai dera hu");

				mc.addRow(new Object[]{selection, collected});
				System.out.println(selection + "  " + collected);
			} else {
				String selection_key = null;
				String result = null;
				ArrayList<String> hashed_avd_ids = new ArrayList<String>();
				for(int i = 0; i < 5; i++){
					try {
						hashed_avd_ids.add(genHash(all_avds[i]));
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					}
				}
				try {
					selection_key = genHash(selection);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				hashed_avd_ids.add(selection_key);
				Collections.sort(hashed_avd_ids);


				int selection_ka_index = hashed_avd_ids.indexOf(selection_key);

				if (selection_ka_index > 0 && selection_ka_index < 5){
					String forward = all_avds[sabka_hashed_ids.indexOf(hashed_avd_ids.get(selection_ka_index+1))];
					String temp = "value_de_iska" + "~" + selection + "~" + forward;
					System.out.println("kisi aur se leke dera hu - " + forward);
					try {
						result = new ClientTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, temp).get();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}

					mc.addRow(new Object[]{selection, result});

				} else {
					if (myavd.equals(parent)) {
						try {
							fis = getContext().openFileInput(selection);
							byte[] input = new byte[fis.available()];
							while (fis.read(input) != -1) {
								collected = new String(input);
							}
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							try {
								fis.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						System.out.println("parent se chahiye - apun parent");
						mc.addRow(new Object[]{selection, collected});

					} else {

						String temp = "value_de_iska" + "~" + selection + "~" + parent;
						System.out.println("parent se leke dera hu");
						try {
							result = new ClientTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, temp).get();
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (ExecutionException e) {
							e.printStackTrace();
						}

						mc.addRow(new Object[]{selection, result});
					}

				}


				hashed_avd_ids.clear();
			}
		}



		return mc;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

    private String genHash(String input) throws NoSuchAlgorithmException {
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        byte[] sha1Hash = sha1.digest(input.getBytes());
        Formatter formatter = new Formatter();
        for (byte b : sha1Hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }



	private class ServerTask extends AsyncTask<ServerSocket, String, Void> {

		@Override
		protected Void doInBackground(final ServerSocket... sockets) {

			final ServerSocket serverSocket = sockets[0];

			//Socket socket;
			//ObjectInputStream in;
			//ObjectOutputStream out;
			String msglere;
			String[] splitmsg;

			System.out.println("Server ban gaya!");

			try {
				while (true) {
					Socket socket = serverSocket.accept();
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

					if ((msglere = in.readLine()) != null){
					//msglere = (String) in.readLine();


					Log.d("msg kya mila?", msglere);

					splitmsg = msglere.split("!");


					if (splitmsg[0].equals("store_kar_isko")) {
						FileOutputStream fos;
						//https://developer.android.com/reference/java/io/FileOutputStream.html

						try {
							fos = getContext().openFileOutput(splitmsg[1], Context.MODE_PRIVATE);
							//https://developer.android.com/reference/android/content/Context.html#openFileOutput(java.lang.String,%20int)
							fos.write(splitmsg[2].getBytes());
							fos.close();
						} catch (Exception e) {
							Log.e("Error", "File write failed");
						}

						Log.v("kardiya insert", splitmsg[1] + "####" + splitmsg[2]);

						try{

							PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
							out.println("store message received by " + myavd);   // send ack that message has been received
							out.flush();
						}catch (Exception e) {
							System.out.println("failed to send insert ack");
						}




					} else if (splitmsg[0].equals("value_de_iska")) {

						String[] path = getContext().getApplicationContext().fileList();
						System.out.println("kitne store karra hu? - " + path.length);
						String collected = null;
						FileInputStream fis = null;

						try {
							fis = getContext().openFileInput(splitmsg[1]);
							byte[] input = new byte[fis.available()];
							while (fis.read(input) != -1) {
								collected = new String(input);
							}
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							try {
								fis.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
						out.println(collected);
						out.flush();

						socket.close();


						System.out.println("yeh le jo value tu manga tha - iska - " + splitmsg[1]);


					} else if (splitmsg[0].equals("givemeeverthing")) {

						String collected = null;
						FileInputStream fis = null;

						String[] path = getContext().getApplicationContext().fileList();

						for (String aPath : path) {
							try {
								fis = getContext().openFileInput(aPath);
								byte[] input = new byte[fis.available()];
								while (fis.read(input) != -1) {
									collected = new String(input);
								}
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							} finally {
								try {
									fis.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							the_real_slim_shady.add(new majormc(aPath, collected));

						}
						System.out.println("majormc ka soize - " + the_real_slim_shady.size());
						PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
						out.println(Integer.toString(the_real_slim_shady.size()));
						out.flush();
						SystemClock.sleep(10);
						for (int i = 0; i < the_real_slim_shady.size(); i++) {
							out.println(the_real_slim_shady.get(i).getKey() + "!" + the_real_slim_shady.get(i).getValue());
							out.flush();
						}

						socket.close();

						the_real_slim_shady.clear();

					} else if (splitmsg[0].equals("deletethis")) {
						getContext().deleteFile(splitmsg[1]);

						try{

							PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
							out.println("delete message received by " + myavd);
							out.flush();
						}catch (Exception e) {
							System.out.println("failed to send delete ack");
						}

						socket.close();
					} else if (splitmsg[0].equals("imalive")){

						PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
						if (someone_dead_ka_insert.isEmpty()) {


							System.out.println("you didnt miss anything");
							out.println("thank you for letting me know");
							out.flush();

						} else {

							System.out.println("yeh le everything you need to know");
							out.println(someone_dead_ka_insert.size());
							out.flush();

							for (String name: someone_dead_ka_insert.keySet()) {
								out.println(name + "!" + someone_dead_ka_insert.get(name));
								out.flush();
							}
							someone_dead_ka_insert.clear();

						}

						socket.close();
					}


				}

				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}
	}


	private class ClientTask extends AsyncTask<String, Void, String> {


		@Override
		protected String doInBackground(String... args) {

			//Socket socket;

			//ObjectInputStream in;
			//ObjectOutputStream out;
			String result;
			String[] temp = args[0].split("~");

			try {
				if (temp[0].equals("store_kar_isko")) {
					Socket socket = new Socket(InetAddress.getByAddress(new byte[]{10, 0, 2, 2}),
							Integer.parseInt(Integer.toString(Integer.parseInt(temp[3]) * 2)));
					try {
						PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

						out.println(temp[0] + "!" + temp[1] + "!" + temp[2]);
						out.flush();
						System.out.println("boldiya usko store karneko - " + temp[1]);

						try {
							BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
							result = in.readLine();

							System.out.println(result);
						} catch (Exception e){
							System.out.println("yeh toh dead hai - " + temp[3]);
							someone_dead_ka_insert.put(temp[1], temp[2]);
						}

					} catch (IOException e) {
						e.printStackTrace();
					}
					socket.close();
				} else if (temp[0].equals("value_de_iska")) {
					Socket socket = new Socket(InetAddress.getByAddress(new byte[]{10, 0, 2, 2}),
							Integer.parseInt(Integer.toString(Integer.parseInt(temp[2]) * 2)));
					try {
						PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
						BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						System.out.println("value request kiya hai " + temp[2] + " se");
						out.println(temp[0] + "!" + temp[1]);
						out.flush();

						try {

							result = in.readLine();
							if (result.equals("null")){
								System.out.println(temp[99]);
							}
							System.out.println("milgaya re value - " + result);
							return result;

						} catch (Exception e){
							int avd_index = 0;
							try {
								avd_index = sorted_hashed_ids.indexOf(genHash(temp[2]));
							} catch (NoSuchAlgorithmException e1) {
								e1.printStackTrace();
							}
							int x = (avd_index + 1) % sorted_hashed_ids.size();
							String forward = all_avds[sabka_hashed_ids.indexOf(sorted_hashed_ids.get(x))];
							String tempp = "value_de_iska" + "!" + temp[1] + "!" + forward;
							System.out.println("age wale se leke dera hu - " + forward);


							socket = new Socket(InetAddress.getByAddress(new byte[]{10, 0, 2, 2}),
									Integer.parseInt(Integer.toString(Integer.parseInt(forward) * 2)));

							out = new PrintWriter(socket.getOutputStream(), true);
							in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
							System.out.println("value request kiya hai");
							out.println(tempp);
							out.flush();

							result = in.readLine();
							System.out.println("milgaya re value - " + result);
							return result;



						}


					} catch (IOException e) {
						e.printStackTrace();
					}
					socket.close();
				} else if (temp[0].equals("givemeeverthing")) {

					int x;
					String gg;
					String[] jj;

					for (String all_avd : all_avds) {

						if (!all_avd.equals(myavd)) {
							Socket socket = new Socket(InetAddress.getByAddress(new byte[]{10, 0, 2, 2}),
									Integer.parseInt(Integer.toString(Integer.parseInt(all_avd) * 2)));


							try {
								PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
								out.println("givemeeverthing" + "!" + temp[1]);
								out.flush();
								BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
								x = Integer.parseInt(in.readLine());

								for (int i = 0; i < x; i++) {
									gg = in.readLine();
									jj = gg.split("!");

									if (!star_storer.containsKey(jj[0])) {
										star_storer.put(jj[0], jj[1]);
									}
								}
							} catch (IOException e) {
								e.printStackTrace();
							}

							socket.close();

						}
					}

					return "done";


				} else if (temp[0].equals("deletethis")) {
					Socket socket = new Socket(InetAddress.getByAddress(new byte[]{10, 0, 2, 2}),
							Integer.parseInt(Integer.toString(Integer.parseInt(temp[2]) * 2)));
					try {
						PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
						out.println("deletethis" + "!" + temp[1]);
						out.flush();
						try {
							BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
							result = in.readLine();
							System.out.println(result);
						}catch (Exception e){
							System.out.println("yeh toh dead hai - " + temp[3]);
						}

					} catch (IOException e) {
						e.printStackTrace();
					}
					socket.close();
				} else if (temp[0].equals("imalive")){
					for (int i = 0; i < all_avds.length; i++){
						if (!myavd.equals(all_avds[i])){
							Socket socket = new Socket(InetAddress.getByAddress(new byte[]{10, 0, 2, 2}),
									Integer.parseInt(Integer.toString(Integer.parseInt(all_avds[i]) * 2)));
							try {
								PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
								out.println("imalive" + "!" + myavd);
								out.flush();

								BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));


								if ((result = in.readLine()) != null) {

									if (result.equals("thank you for letting me know")) {
										System.out.println("Everything is fine");
									} else {
										System.out.println("receiving missed stuff");
										int size = Integer.parseInt(result);
										for (int i1 = 0; i1 < size; i1++) {
											String imissed = in.readLine();
											String[] split_imissed = imissed.split("!");

											FileOutputStream fos;
											//https://developer.android.com/reference/java/io/FileOutputStream.html
											try {
												fos = getContext().openFileOutput(split_imissed[0], Context.MODE_PRIVATE);
												//https://developer.android.com/reference/android/content/Context.html#openFileOutput(java.lang.String,%20int)
												fos.write(split_imissed[1].getBytes());
												fos.close();
											} catch (Exception e) {
												Log.e("Error", "File write failed");
											}
										}
									}

								}


							} catch (IOException e) {
								Log.d("im alive broadcast", "initial error, others are not awake yet");
							}
							socket.close();

						}
					}
				}





			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return "kuchnai";
		}
	}











}
