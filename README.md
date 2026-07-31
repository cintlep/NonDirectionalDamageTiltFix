This mod is a lightweight mixin fix for [MC-259212] Directional damage tilt is still used for non-directional damage sources after taking damage from a directional damage source

Info
Directional damage tilt was in singleplayer minecraft from its conception, but broke in 1.3 due to the server and client merge. They fixed it in 1.19.4 but forgot to add the check for whether your damage was locational or not.

This mod fixes this by adding that missing check. If its directional, tilt from that direction. If its not, tilt left or right, just like how it was before it was broken.

Usage
Mod requires no setup or configuration. Drop it in your fabric/neoforge mods folder for your version and it works on every server.
