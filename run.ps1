
Write-Host "Cleaning previous build..."

if (Test-Path build) {
    Remove-Item -Recurse -Force build
}

Write-Host "Creating build directory..."
New-Item -ItemType Directory -Path build | Out-Null

Write-Host "Compiling sources..."
javac -d build src\main\java\arbreb\*.java

if ($LASTEXITCODE -ne 0) {
    Write-Host "Compilation failed."
    exit 1
}

Write-Host "Running application..."
java -cp build arbreb.ArbreB
