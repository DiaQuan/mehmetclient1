# Mehmet Client 0.2.0

A client-side Fabric mod project for Minecraft Java Edition 1.21.11.

## Implemented in this build
- Right Shift opens/closes the purple-and-black Mehmet Client menu.
- Tabs for General, BaseFinding, and Render.
- Clickable module toggle registry for the requested module list.
- Functional HUD toggle (enabled by default) showing the client name, coordinates, and enabled module count.
- GitHub Actions workflow builds and uploads the remapped JAR artifact.

## Important status note
The menu and module toggle registry are functional. Apart from the HUD display, most listed modules are currently UI entries only; their gameplay behavior has not been implemented yet. This build does not claim anti-cheat undetectability and does not include anti-cheat bypass code.

## Build on GitHub Actions
1. Upload the repository contents to a GitHub repository.
2. Open **Actions** and run **Build Mehmet Client JAR** (it also runs on pushes and pull requests).
3. Open the completed workflow run and download the JAR from **Artifacts**.
4. Install Fabric Loader for Minecraft 1.21.11, then place the JAR in the instance's `mods` folder. Fabric API is required too.

## Local build
Requires Java 21 and Gradle 9.2.1:

```powershell
gradle --no-daemon clean build
```

Output: `build/libs/mehmetclient-0.2.0.jar`.
