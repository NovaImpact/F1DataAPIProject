# F1 Data API JavaFX Project 🏎️

## ✅ READY TO IMPORT INTO INTELLIJ IDEA

This is a complete, ready-to-run JavaFX project. Just import and run!

---

## 🚀 HOW TO USE

### Step 1: Open in IntelliJ IDEA
1. Open IntelliJ IDEA
2. **File → Open**
3. Select the **F1DataAPIProject** folder
4. Click **OK**
5. Wait 30 seconds for Maven to download dependencies (watch bottom right)

### Step 2: Run the Application
1. In the left panel, navigate to:
   ```
   src → main → java → com.example.dataapiproject → Launcher
   ```
2. Right-click on **Launcher.java**
3. Click **"Run 'Launcher.main()'"**
4. Application window opens!

### Step 3: Use the App
1. Click the **"Fetch F1 Data"** button
2. Wait 2-3 seconds
3. See F1 practice session results in the table!

---

## 📁 What's Included

✅ **Model Class** - `F1ApiData.java` (stores driver data)  
✅ **View** - `hello-view.fxml` (UI layout - SceneBuilder compatible)  
✅ **Controller** - `HelloController.java` (fetches API, parses JSON)  
✅ **Main App** - `HelloApplication.java`  
✅ **Launcher** - `Launcher.java` (entry point - RUN THIS)  
✅ **Maven Config** - `pom.xml` (dependencies auto-download)  
✅ **IntelliJ Config** - `.idea/` folder (pre-configured)

---

## ✨ Features

- Fetches real F1 data from https://f1api.dev
- Parses JSON into Java Model objects
- Displays in professional TableView with 5 columns
- Error handling and status updates
- No API key required

---

## 🎯 Assignment Requirements Met

✅ JavaFX Application created  
✅ Model class with proper structure  
✅ View designed (FXML file)  
✅ Controller implements logic  
✅ Fetches JSON from API  
✅ Parses JSON to Model objects  
✅ **Exceeds Expectations**: Uses TableView for multiple data objects

---

## 🐛 Troubleshooting

### "Maven dependencies not downloading"
**Solution:**
- Check internet connection
- Right-click `pom.xml` → Maven → Reload Project

### "Cannot find JavaFX"
**Solution:**
- File → Project Structure → Project → SDK: Select Java 17
- File → Project Structure → Modules → Dependencies: Verify Maven dependencies loaded

### "Module errors"
**Solution:**
- Make sure you run **Launcher.java** (not HelloApplication.java)

---

## 📊 What You'll See

When you run the app, a table displays:
- **Position** - Driver rank (1-20)
- **Driver** - Full name (e.g., "Max Verstappen")
- **Team** - Constructor (e.g., "Red Bull Racing")
- **Best Time** - Fastest lap (e.g., "1:29.945")
- **Gap** - Time behind leader (e.g., "+0.234")

---

## 🔧 Customization

### Change the API endpoint:
In `HelloController.java`, line 55:
```java
String yourAPIurl = "https://f1api.dev/api/2024/1/fp1";
```

Other options:
- `/api/2024/1/fp2` - Free Practice 2
- `/api/2024/1/qualifying` - Qualifying
- `/api/2024/1/race` - Race results

### Edit UI in SceneBuilder:
1. Download SceneBuilder: https://gluonhq.com/products/scene-builder/
2. Right-click `hello-view.fxml`
3. Open With → SceneBuilder
4. Edit visually and save

---

## 📚 Technologies Used

- **JavaFX 21** - UI framework
- **Maven** - Dependency management
- **org.json** - JSON parsing
- **F1 API** - Real-time F1 data

---

## 📝 File Structure

```
F1DataAPIProject/
├── .idea/                    ← IntelliJ configuration
├── src/
│   └── main/
│       ├── java/
│       │   ├── module-info.java
│       │   └── com/example/dataapiproject/
│       │       ├── Launcher.java          ← RUN THIS
│       │       ├── HelloApplication.java
│       │       ├── HelloController.java
│       │       └── F1ApiData.java
│       └── resources/
│           └── com/example/dataapiproject/
│               └── hello-view.fxml
├── pom.xml                   ← Maven dependencies
└── README.md                 ← This file
```

---

## 🎓 Good Luck!

This project is ready to submit. Just import, run, and show your instructor!

**Questions?** Check the console output for error messages.

---

**Made with ❤️ for JavaFX assignments**
