# Planned features

TODO:
- support an edition of entities with composite primary keys (for now available just in table review mode);

# Changelog
**1.0.2**

UPDATES:
- support an edition of entities with composite primary keys (@EmbeddedId and @IdClass)

**1.0.1**

UPDATES:
- Spring boot updated to version 3.5.7 for security purposes and native supporting `mvn clean install` with a new bytebuddy version

**1.0.0**

FIXES:
- SPEL expression execution throws exceptions in the new Spring boot (relates with new introduced security in Spring boot for SPEL)
- UUID field type parsing;
- pagination and parametrized queries;
- OffsetDateTimeFieldType parser;
- collect all fields for the entity (before extended class fields were missed);
- collect all getters and setters for entity;
- settings page;
- Entity record creation with field types (timestamp); 
- entity record search engine;

FEATURES:
- added logout button for security mode;
- added functionality for storing Snapadmin service data into the same DB with the application (for that we should set 'enabledAppInternalDs: true' and if it's not a standard DS, then set settings prefix 'appInternalDsSettingsPrefix: spring.customdatasource');