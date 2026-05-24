# OpenArena2026

**Author**: Andres Villagran <andres@villagranquiroz.cl>

A modern Android port of **OpenArena**, based on the [openarena-ioq3](https://github.com/OpenArena-Ioq3/openarena-ioq3) engine.

## Featured Servers - ggup.cl

Quick-connect to our community servers:
- **q3.ggup.cl:27960** - FFA / TDM
- **q3ctf.ggup.cl:27961** - Capture The Flag

You can also connect to any other Quake 3 / OpenArena server via the in-game console.

## Graphics

- OpenGL ES 2+ renderer (based on ioquake3 opengl2)
- Optimized for mobile GPUs
- Supports both phones and tablets
- Adjustable quality settings for performance

## Assets

### OpenArena (Default - GPL)
The app includes free OpenArena 0.8.8 assets by default. Place `.pk3` files in:
```
Android/data/cl.villagranquiroz.openarena2026/files/baseoa/
```

### Quake III Arena (Optional - Import your own)
If you own Quake III Arena, import your `pak0.pk3` - `pak8.pk3` files via the **Import Q3A** button in the launcher.

> We do NOT distribute copyrighted assets. You must provide your own Q3A files.

## Building

### Requirements
- Android Studio Ladybug+
- Android NDK r26b+
- CMake 3.22+
- Android SDK API 34+

### Steps
```bash
git clone https://github.com/avillagran/openarena2026.git
cd openarena2026
# Open android/ folder in Android Studio and build
```

### Download Assets
```bash
./scripts/download-assets.sh
```

## License

- Engine: **GPL v2** (ioquake3 / openarena-ioq3)
- OpenArena assets: **GPL v2**
- Quake III Arena assets: Property of id Software / Bethesda (not included)

## Original Engine Documentation

For full engine documentation (cvars, commands, build options, etc.), see the original [openarena-ioq3 README](https://github.com/OpenArena-Ioq3/openarena-ioq3/blob/main/README.md).
