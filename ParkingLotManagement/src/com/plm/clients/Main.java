package com.plm.clients;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.plm.app.FeeRange;
import com.plm.app.ParkingLot;
import com.plm.constants.VehicleTypes;
import com.plm.data.Vehicle;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main {
	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("Please Provice the Parking lot config file");
		} else {
			ParkingLot parkingLot = initParkingLot(args[0]);
			if (args.length == 2) {
				System.out.println("Processing through Data File");
				handlingParking(args[1], parkingLot);
			} else {
				System.out.println("Process Through Live interaction");
				try (Scanner scan = new Scanner(System.in)) {
					String vehicleType = "";
					while (true) {
						System.out.print("You want to park or unpark a vehicle : ");
						String action = scan.nextLine();
						if (action.equalsIgnoreCase("park")) {
							System.out.print("Enter the vehicle type : ");
							vehicleType = scan.nextLine();
							System.out.println("Enter Vehicle Number : ");
							String vehicleNumber = scan.nextLine();
							Vehicle vehicle = getVehicleInstance("com.plm.data." + vehicleType, parkingLot);
							if (vehicle != null) {
								vehicle.setVehicleInfo(vehicleNumber, new Date(System.currentTimeMillis()));
								System.out.println(parkingLot.park(vehicle));
							} else {
								System.out.println("Vehicle Type Not supported");
							}
						} else if (action.equalsIgnoreCase("unpark")) {
							System.out.println("Enter Vehicle Number : ");
							String vehicleNumber = scan.nextLine();
							Vehicle vehicle = parkingLot.getVehicles().get(vehicleNumber);
							//Set Exit Time : Bump it by 10 mins for practicality purposes.
							vehicle.setExitTime(new Date(System.currentTimeMillis() + (1000 * 60 * 10))); 
							System.out.println(parkingLot.unPark(vehicleNumber));
						} else if (action.equalsIgnoreCase("exit")) {
							System.out.println("Thank you for using the application");
							break;
						} else {
							System.out.println("Invalid action");
						}
						System.out.println(parkingLot);
					}
				}
			}
		}
	}

	private static Vehicle getVehicleInstance(String className, ParkingLot pl) {
		Vehicle v = null;
		try {
			v = (Vehicle) (Class.forName(className).getConstructor(ParkingLot.class).newInstance(pl));
		} catch (ClassNotFoundException e) {
			return null;
		}
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return v;
	}

	@SuppressWarnings("unchecked")
	private static ParkingLot initParkingLot(String fileName) {
		try {
			String jsonString = new String(Files.readAllBytes(Paths.get(fileName)));

			JSONObject json = new JSONObject(jsonString);
			int LMVSlots = Integer.valueOf(json.getString("LMVSlots"));
			int HTVSlots = Integer.valueOf(json.getString("HTVSlots"));
			int TWVSlots = Integer.valueOf(json.getString("TWVSlots"));

			ParkingLot parkingLot = new ParkingLot(LMVSlots, TWVSlots, HTVSlots);
			JSONObject feeRangesObj = json.getJSONObject("feeRanges");
			HashMap<VehicleTypes, FeeRange[]> map = new HashMap<VehicleTypes, FeeRange[]>();
			Iterator<String> keys = feeRangesObj.keys();
			while (keys.hasNext()) {
				String next = keys.next();
				JSONArray jsonArray = feeRangesObj.getJSONArray(next);
				FeeRange[] range = new FeeRange[jsonArray.length()];
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					range[i] = new FeeRange(jsonObject.getInt("startHour"), jsonObject.getInt("endHour"),
							jsonObject.getInt("rate"), jsonObject.getBoolean("isDaily"),
							jsonObject.getBoolean("isHourly"));
				}
				map.put(VehicleTypes.valueOf(next), range);
			}
			parkingLot.setFeeRanges(map);
			parkingLot.setFeesType(json.getString("FeesType"));
			return parkingLot;
		} catch (Exception e) {
			System.err.println("Error reading or parsing JSON file: " + e.getMessage());
			return null;
		}
	}

	private static void handlingParking(String fileName, ParkingLot parkingLot) {
		try {
			String jsonString = new String(Files.readAllBytes(Paths.get(fileName)));
			System.out.println(jsonString);
			JSONArray steps = new JSONArray(jsonString);
			for (int i = 0; i < steps.length(); i++) {
				JSONObject step = steps.getJSONObject(i);
				if (step.getString("action").equalsIgnoreCase("park")) {
					Vehicle vehicle = getVehicleInstance("com.plm.data." + step.getString("vehicleType"), parkingLot);
					vehicle.setVehicleInfo(step.getString("vehicleNumber"), getDate(step.getString("entryTime")));
					String parkingInfo = parkingLot.park(vehicle);
					System.out.println(parkingInfo);
				} else if (step.getString("action").equalsIgnoreCase("unpark")) {
					Vehicle vehicle = parkingLot.getVehicles().get(step.getString("vehicleNumber"));
					if (vehicle != null) {
						vehicle.setExitTime(getDate(step.getString("exitTime")));
						String unParkingInfo = parkingLot.unPark(step.getString("vehicleNumber"));
						System.out.println(unParkingInfo);
					}
				}

			}

		} catch (IOException | JSONException e) {
			System.err.println("Error reading or parsing JSON file: " + e.getMessage());
		}

	}

	private static Date getDate(String dateInString) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
		try {
			return formatter.parse(dateInString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

}
