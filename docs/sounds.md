# Sounds

Sound are usually an important part of any game. A sound can be either an
effect that is part of the game, or an audio-fragment that is being played at
the background. Both cases are supported by Yaeger, but both require a different
way to make them happen.

## Background audio

Background audio can be set on either the `YaegerGame` or `YaegerScene`. In
both cases the audio will loop indefinitely until respectively the `YaegerGame`
has ended or a different `YaegerScene` is loaded. This way it is possible to
set background that will be used throughout the game, or is specific to a
`YaegerScene`.

## Sound effects

When a sound effect should be played, a `SoundClip` can be used. It will only
be played once and as soon as it has finished it becomes eligible for garbage
collection. Although the volume of a `SoundClip` can be set, note that it is
only applied to the next time it is played. Thus, the volume has to be set
before `play()` is called.
