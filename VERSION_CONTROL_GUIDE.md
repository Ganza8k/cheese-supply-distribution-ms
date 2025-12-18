# Version Control Setup Guide

## Git Setup Instructions

### 1. Initialize Git Repository
```bash
cd "c:\Users\ganza\Documents\Best Programming\FinalExam\CheeseSupplyManagementSystem"
git init
git add .
git commit -m "Initial commit: Cheese Supply Management System"
```

### 2. Create GitHub Repository
1. Go to https://github.com
2. Click "New Repository"
3. Name: CheeseSupplyManagementSystem
4. Click "Create Repository"

### 3. Connect Local to Remote
```bash
git remote add origin https://github.com/YOUR_USERNAME/CheeseSupplyManagementSystem.git
git branch -M main
git push -u origin main
```

### 4. NetBeans Git Integration
1. Open NetBeans
2. Go to Team → Git → Initialize Repository
3. Select project folder
4. Right-click project → Git → Commit
5. Right-click project → Git → Remote → Push

### 5. Common Git Commands
```bash
# Check status
git status

# Add changes
git add .

# Commit changes
git commit -m "Description of changes"

# Push to remote
git push origin main

# Pull from remote
git pull origin main

# Create new branch
git checkout -b feature-name

# View commit history
git log
```

## Version Control Best Practices
- Commit frequently with meaningful messages
- Use branches for new features
- Pull before pushing
- Review changes before committing
- Keep commits small and focused