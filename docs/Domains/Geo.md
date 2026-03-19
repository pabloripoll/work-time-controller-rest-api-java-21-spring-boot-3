<div id="top-header" style="with:100%;height:auto;text-align:right;">
    <img src="./../images/pr-banner-long.png">
</div>

# WORKTIME CONTROLLER - JAVA SPRING BOOT 3

- [/README.md](../README.md)
<br><br>

# Domain Geographic Locations

Hierarchical self-referential table for geo locations - this is actually a design pattern called **Adjacency List** or **Closure Table** pattern. This avoids the complexity of multiple tables.

## Example Query to Get Full Hierarchy

```php
// Get complete hierarchy for a city
$city = $geoRepo->findById(10); // Some city

$hierarchy = [
    'continent' => $city->getContinentId() ? $geoRepo->findById($city->getContinentId())->getName() : null,
    'zone' => $city->getZoneId() ? $geoRepo->findById($city->getZoneId())->getName() : null,
    'country' => $city->getCountryId() ? $geoRepo->findById($city->getCountryId())->getName() : null,
    'region' => $city->getRegionId() ? $geoRepo->findById($city->getRegionId())->getName() : null,
    'state' => $city->getStateId() ? $geoRepo->findById($city->getStateId())->getName() : null,
    'city' => $city->getName(),
];

// Result: Europe > EU > Spain > Comunitat Valenciana > Valencia > Valencia City
```

<!-- FOOTER -->
<br>

---

<br>

- [GO TOP ⮙](#top-header)

<div style="with:100%;height:auto;text-align:right;">
    <img src="./../images/pr-banner-long.png">
</div>