# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Firebase Hosting project for "Fiesta Global" - appears to be an event scheduling application with artist performances. The project serves static content through Firebase Hosting.

## Architecture

- **Frontend**: Static HTML/CSS/JavaScript served via Firebase Hosting
- **Data**: JSON files containing event schedules and artist information
- **Firebase Services**: Configured for hosting with emulator support enabled

## Key Files

- `firebase.json`: Firebase configuration for hosting setup
- `public/`: Static assets directory containing:
  - `index.html`: Main Firebase welcome page with SDK initialization
  - `data.json`: Event schedule data with artists, times, and locations
  - `data_with_images.json`: Extended event data (likely includes image URLs)
  - `404.html`: Custom 404 error page

## Development Commands

This project uses Firebase CLI for deployment and local development:

```bash
# Serve locally with Firebase emulator
firebase serve

# Deploy to Firebase Hosting
firebase deploy
```

## Firebase Configuration

The project is configured with:
- Firebase Hosting pointing to `public/` directory
- Firebase emulator enabled (`useEmulator=true` in init.js)
- Multiple Firebase services initialized (auth, database, firestore, functions, messaging, storage, analytics, remote config, performance)

## Data Structure

Event data follows this structure:
```json
{
  "schedule": [
    {
      "day": "Gio 17",
      "artists": [
        {
          "name": "ARTIST_NAME",
          "time": "18:00",
          "location": "VENUE_NAME"
        }
      ]
    }
  ]
}
```

## Notes

- The project appears to be in Italian (event data contains Italian text)
- Firebase SDK version 11.9.0 is used
- Development mode is enabled with emulator support